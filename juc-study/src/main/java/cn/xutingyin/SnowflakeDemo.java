package cn.xutingyin;

import java.util.HashMap;
import java.util.Map;

import xyz.downgoon.snowflake.Snowflake;

/**
 * @description:Snowflake 生成Id使用生成重复ID:在代码中每次生成id时new Snowflake().nextId()获取新的id。如果极短时间调用有可能会生成重复id。
 * @author: xuty
 * @date: 2020/6/18 17:24
 */

public class SnowflakeDemo {
    /**
     * 测试很快就出现重复
     */
    public static void test1() {
        Map<Long, Long> map = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            // new Snowflake().nextId()获取新的id。如果极短时间调用有可能会生成重复id。
            long id = new Snowflake(2, 5).nextId();
            if (map.containsKey(id)) {
                System.out.println("出现重复");
                break;
            }
            System.out.println("id=*********" + id);
            map.put(id, id);
        }
    }

    /**
     * 测试不会出现重复
     */
    public static void test2() {
        Snowflake snowflake = new Snowflake(2, 5);
        Map<Long, Long> map = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            long id = snowflake.nextId();
            if (map.containsKey(id)) {
                System.out.println("出现重复");
                break;
            }
            System.out.println("id=*********" + id);
            map.put(id, id);
        }
    }

    public static void main(String[] args) {
        test1();
        // test2();
    }

}
