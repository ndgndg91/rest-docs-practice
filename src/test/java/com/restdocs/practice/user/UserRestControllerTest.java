package com.restdocs.practice.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restdocs.practice.user.dto.SignUpRequest;
import com.restdocs.practice.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void findById() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .fullName("Dong-gil")
                .email("ndgndg91@gmail.com")
                .password("123123")
                .build();
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        UserResponse response = new UserResponse(user);

        //when
        String body = new ObjectMapper().writer().writeValueAsString(response);
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/apis/users/{id}", user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(body));

        //then
        resultActions.andDo(document("find-by-id",
                pathParameters(
                        parameterWithName("id").description("The user id")
                ),
                responseFields(
                        fieldWithPath("id")
                                .description("The user id"),
                        fieldWithPath("fullName")
                                .description("The user full name"),
                        fieldWithPath("email")
                                .description("The user email.")
                )
        ));
    }

    @Test
    void SignUp() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest("ndgndg91@gmail.com", "Dong-Gil", "123123");
        String requestBody = new ObjectMapper().writer().writeValueAsString(request);
        given(userRepository.save(Mockito.any(User.class))).will(i -> i.getArguments()[0]);

        //when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/apis/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andDo(print())
                .andExpect(status().isCreated());
        //then
        resultActions.andDo(document("sign-up",
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("The user Email"),
                        fieldWithPath("fullName").type(JsonFieldType.STRING).description("The user fullName"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("The user passsword")
                ),
                responseHeaders(
                        headerWithName("Location").description("user resource")
                )
        ));
    }
}

