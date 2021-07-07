package cn.mayu.yugioh.common.basic.tool;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 对象属性拷贝，使用get set
 */
@Mapper(componentModel = "spring")
public interface ApiMapper {

    /**
     * 静态调用
     */
    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);

    @Mapping(source = "a2", target = "a")
    AA apiVoToApiDto(AA2 apiVO);
}

class A {

    public static void main(String[] args) {
        AA2 aa2 = new AA2();
        aa2.setA2(1);
        AA aa = ApiMapper.INSTANCE.apiVoToApiDto(aa2);
        System.out.println(aa);
    }
}

@Data
class AA {

    private int a;
}

@Data
class AA2 {

    private int a2;
}