package com.slay.outliers.member.infra;

import com.slay.outliers.member.domain.Member;
import com.slay.outliers.member.domain.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long>, MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);
}
