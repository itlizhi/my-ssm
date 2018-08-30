package com.enreach.ssm.utils;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.stream.Collectors;

//https://www.cnblogs.com/peida/archive/2013/06/08/3120820.html
public class GuavaTest {


    @Test(expected = IllegalArgumentException.class)
    public void base() {

        Integer value = 3;// null;
        Optional<Integer> possible = Optional.of(value); // T不能为空，为空会报错

        //Optional<T> 可以忘记null，
        System.out.println(possible.get());

        //不符合会抛出Exception
        Preconditions.checkArgument(value >= 3, "value 需要 >= 3");
        Preconditions.checkNotNull(value, "不能为null");

        try {
            Preconditions.checkArgument(CheckEmpty.isNotEmpty(""), "不能为空");
        } catch (Throwable t) {
            System.out.println(Throwables.getStackTraceAsString(t));
            Throwables.propagateIfPossible(t, IllegalArgumentException.class);
        }

    }

    @Test
    public void ordering() {

        List<String> list = Lists.newArrayList();
        list.add("lizhi");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("join");
        list.add("neron");

        /**
         * natural()：使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;
         　usingToString() ：使用toString()返回的字符串按字典顺序进行排序；
         */
        Ordering o0 = Ordering.natural();

        System.out.println(o0.sortedCopy(list));

        System.out.println(o0.reverse().sortedCopy(list));

        //对类的排序

        List<UserTmp> temps = Lists.newArrayList();
        temps.add(new UserTmp("A", 11));
        temps.add(new UserTmp("C", 15));
        temps.add(new UserTmp("B", 15));
        temps.add(new UserTmp("E", 10));

        Ordering o1 = Ordering.natural().onResultOf((Function<UserTmp, Comparable>) input -> input.getAge()).reverse();
        System.out.println(o1.sortedCopy(temps));


        Ordering o2 = Ordering.from(new Comparator<UserTmp>() {
            @Override
            public int compare(UserTmp o1, UserTmp o2) {
                if (o1.getAge() == o2.getAge()) { //复合查询
                    return o1.getName().compareTo(o2.getName());//从小到大
                } else {
                    return o1.getAge() - o2.getAge();
                }
            }
        });

        System.out.println("02:" + o2.sortedCopy(temps));

        Ordering o3 = Ordering.from(new Comparator<UserTmp>() {
            @Override
            public int compare(UserTmp o1, UserTmp o2) {
                return o1.getName().compareTo(o2.getName()); //从小到大
            }
        });

        System.out.println(o3.sortedCopy(temps));


        //复合排序
        Ordering o4 = o1.compound(o3);
        System.out.println(o4.sortedCopy(temps));


        //使用 lambda
        System.out.println(temps.stream().sorted(Comparator.comparing(UserTmp::getAge).reversed().thenComparing(UserTmp::getName))
                .collect(Collectors.toList()));


    }

    /**
     * 不可变集合
     */
    @Test
    public void immutable() {

        ImmutableList<String> immutableList = ImmutableList.of("a", "b", "c");
        //immutableList.add("e");

        List<String> list = Lists.newArrayList(immutableList);

        ImmutableList<String> immutableList1 = ImmutableList.copyOf(list);

        list.add("e");

        System.out.println(immutableList);
        System.out.println(list);
        System.out.println(immutableList1);

        assertThat(immutableList.get(0)).isEqualTo("a");
        assertThat(immutableList.asList().get(1)).isEqualTo("b");
        assertThat(immutableList.contains("c")).isTrue();

        /**
         * Collection	ImmutableCollection
         List	        ImmutableList
         Set		    ImmutableSet
         SortedSet/NavigableSet		ImmutableSortedSet
         Map		    ImmutableMap
         */

    }


    /**
     * 新增的set & map
     */
    @Test
    public void extendSetMap() {

        //Multiset
        List<String> list = Arrays.asList("a", "b", "a", "c");
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(list);
        multiset.add("c");
        //elementSet : 将不同的元素放入一个Set中
        for (String key : multiset.elementSet()) {
            System.out.println(key + " count：" + multiset.count(key));
        }

    }


    private class UserTmp {
        private String name;
        private Integer age;

        public UserTmp(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserTmp{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


}
