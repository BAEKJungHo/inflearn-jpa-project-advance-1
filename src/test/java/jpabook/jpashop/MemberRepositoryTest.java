package jpabook.jpashop;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 에게 SpringBootTest 를 사용한다는 것을 알려준다.
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


}