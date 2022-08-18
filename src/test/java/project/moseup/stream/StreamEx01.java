package project.moseup.stream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEx01 {

    @Test
    public void stream1_test(){
        List<data> datas = new ArrayList<>();
        datas.add(new data(1L, "테스트", 12000));
        datas.add(new data(2L, "테스트2", 22000));

        // datas.stream(); 스트림 타입으로 변경
        Stream<data> stream1 = datas.stream();
        // data::getName 데이터 클래스 타입의 getName 호출하겠다. * 중요 (최종 연산을 할 때 실행됨)
        Stream<String> stream2 = stream1.map(data::getName);

        stream2.forEach((str) -> {
            System.out.println(str);
        });
    }

    @Test
    public void stream2_test(){
        List<data> datas = new ArrayList<>();
        datas.add(new data(1L, "테스트", 12000));
        datas.add(new data(2L, "테스트2", 22000));

        // datas.stream(); 스트림 타입으로 변경
        Stream<data> stream1 = datas.stream();
        // data::getName 데이터 클래스 타입의 getName 호출하겠다. (최종 연산을 할 때 실행됨)
        Stream<String> stream2 = stream1.map(data::getName);

        stream2.collect(Collectors.toList());

    }
}
