package com.slay.outliers.member.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slay.outliers.member.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    @ApiModelProperty(position = 0, example = "1(고유번호)")
    private Long id;
    @ApiModelProperty(position = 1, example = "uo5234@naver.com(이메일)")
    private String email;
    @ApiModelProperty(position = 2, example = "박민욱(이름)")
    private String name;

    @Builder
    public MemberResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
