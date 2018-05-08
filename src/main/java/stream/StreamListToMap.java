package stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-5-7
 * Time: 16:05
 * info:
 */
public class StreamListToMap {


    static class Foo {
        int id;
        String name;

        Foo(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void listToMap1(){
        List<Foo> foos = new ArrayList<>();
        Foo foo1 = new Foo(1,"allen");
        Foo foo2 = new Foo(2,"Java");
        foos.add(foo1);
        foos.add(foo2);
        Map<Integer, Foo> map = foos.stream().collect(Collectors.toMap(Foo::getId, Foo->Foo));

        JSONObject jsonObject = (JSONObject) JSON.toJSON(map);
        System.out.println("map = " + jsonObject.toString());

    }

    /**
     * map使用
     */
    public static void streamMap(){
        List<Foo> foos = new ArrayList<>();
        Foo foo1 = new Foo(1,"allen");
        Foo foo2 = new Foo(2,"Java");
        foos.add(foo1);
        foos.add(foo2);
        //获取对象的id
        List list1 = foos.stream().map(foo -> foo.getId()).collect(Collectors.toList());
        //id*2
        List list2 = foos.stream().map(foo -> foo.getId() * 2 ).collect(Collectors.toList());
        //转换大写
        List list3 = foos.stream().map(foo -> foo.getName().toUpperCase() ).collect(Collectors.toList());

        System.out.println("list1 = " + list1);

        System.out.println("list2 = " + list2);

        System.out.println("list3 = " + list3);
    }

    public static void main(String[] args) {
        //listToMap1();
        streamMap();
    }
}
