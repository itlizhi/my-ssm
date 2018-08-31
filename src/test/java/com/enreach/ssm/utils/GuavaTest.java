package com.enreach.ssm.utils;

import com.enreach.ssm.dto.ArticleDto;
import com.enreach.ssm.entity.Article;
import com.enreach.ssm.infrastructure.BizException;
import com.enreach.ssm.utils.concurrent.ThreadUtil;
import com.google.common.base.*;
import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//https://www.cnblogs.com/peida/archive/2013/06/08/3120820.html
//http://ifeve.com/google-guava-eventbus/
public class GuavaTest {

    @Test(expected = IllegalArgumentException.class)
    public void base() {

        System.out.println(Splitter.on(",").trimResults().omitEmptyStrings().splitToList("a,f,,h ,"));

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

        List<UserTemp> temps = Lists.newArrayList();
        temps.add(new UserTemp("A", 11));
        temps.add(new UserTemp("C", 15));
        temps.add(new UserTemp("B", 15));
        temps.add(new UserTemp("E", 10));

        Ordering o1 = Ordering.natural().onResultOf((Function<UserTemp, Comparable>) input -> input.getAge()).reverse();
        System.out.println(o1.sortedCopy(temps));


        Ordering o2 = Ordering.from(new Comparator<UserTemp>() {
            @Override
            public int compare(UserTemp o1, UserTemp o2) {
                if (o1.getAge() == o2.getAge()) { //复合查询
                    return o1.getName().compareTo(o2.getName());//从小到大
                } else {
                    return o1.getAge() - o2.getAge();
                }
            }
        });

        System.out.println("02:" + o2.sortedCopy(temps));

        Ordering o3 = Ordering.from(new Comparator<UserTemp>() {
            @Override
            public int compare(UserTemp o1, UserTemp o2) {
                return o1.getName().compareTo(o2.getName()); //从小到大
            }
        });

        System.out.println(o3.sortedCopy(temps));


        //复合排序
        Ordering o4 = o1.compound(o3);
        System.out.println(o4.sortedCopy(temps));


        //使用 lambda
        System.out.println(temps.stream().sorted(Comparator.comparing(UserTemp::getAge).reversed().thenComparing(UserTemp::getName))
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

    @Test
    public void multiset() {
        List<String> list = Arrays.asList("a", "b", "a", "c");
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(list);
        multiset.add("c");
        //elementSet : 将不同的元素放入一个Set中
        for (String key : multiset.elementSet()) {
            System.out.println(key + " count：" + multiset.count(key));
        }


    }

    @Test
    public void multimap() {

        //Multimap，一般的map key相同的情况下，会覆盖前面的值，Multimap不会
        Multimap<String, UserTemp> multimap = ArrayListMultimap.create();
        for (int i = 0; i < 20; i++) {
            UserTemp userTemp = new UserTemp("name-" + i, i);
            multimap.put("key0", userTemp);
        }
        for (int i = 30; i < 40; i++) {
            UserTemp userTemp = new UserTemp("name-" + i, i);
            multimap.put("key1", userTemp);
        }

        System.out.println(multimap.size());
        System.out.println(multimap.keys());

        //相当于可以group by
        Map<String, Collection<UserTemp>> maps = multimap.asMap();
        for (Map.Entry<String, Collection<UserTemp>> entry : maps.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().size());
        }
    }

    @Test
    public void biMap() {
        //BiMap
        BiMap<Integer, String> biMap = HashBiMap.create();
        biMap.put(1, "a");
        biMap.put(2, "b");
        //biMap.put(1, "d");//会覆盖
        //biMap.put(3, "a"); //value 不能重复

        biMap.put(3, "c");
        System.out.println(biMap.get(1));

        BiMap<String, Integer> b2 = biMap.inverse();//反转
        b2.put("d", 4); //put 后 原始的biMap也会改变

        System.out.println(b2.get("c"));
        System.out.println(biMap);
        System.out.println(b2);

    }

    @Test
    public void table() {

        //行 列 值
        Table<String, Integer, String> table = HashBasedTable.create();
        table.put("a", 1, "a1");
        table.put("a", 2, "a2");
        table.put("b", 1, "b1");
        table.put("b", 2, "b2");
        table.put("a", 2, "ab2");//会覆盖

        System.out.println(table.row("a"));
        System.out.println(table.column(1));

        System.out.println(table.contains("a", 2));
        System.out.println(table.containsRow("c"));
        System.out.println(table.containsColumn(2));

        System.out.println(table.columnMap());
        System.out.println(table.rowMap());

        System.out.println(table);


    }

    @Test
    public void mutableClass() {

        //以class当作key
        ClassToInstanceMap<Object> classToInstanceMap = MutableClassToInstanceMap.create();
        classToInstanceMap.putInstance(UserTemp.class, new UserTemp("a", 1));
        classToInstanceMap.putInstance(ArticleDto.class, new ArticleDto());
        System.out.println(classToInstanceMap.get(ArticleDto.class));
        System.out.println(classToInstanceMap);

    }


    /**
     * 重点： cache key值是通过hashCode计算的  ArticleDto重写 【hashCode】 和 【equals】 (这个必须有) 方法后可以识别一样
     */
    @Test
    public void loadingCache() {

        //LoadingCache  全局定义
        LoadingCache<ArticleDto, Article> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(10, TimeUnit.SECONDS).build(new CacheLoader<ArticleDto, Article>() {
            @Override
            public Article load(ArticleDto key) throws Exception {
                Article article = new Article();
                article.setTitle(key.getTitle());
                System.out.println("run load");
                return article;
            }
        });

        ArticleDto dto = new ArticleDto();
        dto.setTitle("abc");

        ArticleDto dto2 = new ArticleDto();
        dto2.setTitle("abc");

        System.out.println(dto.hashCode());
        System.out.println(dto2.hashCode());

        try {
            System.out.println(cache.get(dto));
            System.out.println(cache.get(dto2));
            dto2.setCreator("lizhi");
            System.out.println(cache.get(dto2));

        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void cacheCallable() {

        ArticleDto dto = new ArticleDto();
        dto.setTitle("abc");

        ArticleDto dto2 = new ArticleDto();
        dto2.setTitle("abc");

        System.out.println(getData(dto));
        System.out.println(getData(dto2));
    }

    Cache<ArticleDto, Article> cacheMain = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(10, TimeUnit.SECONDS).build();

    /**
     * 模拟长时间方法
     *
     * @param dto
     * @return
     */
    private Article getData(ArticleDto dto) {

        try {
            return cacheMain.get(dto, new Callable<Article>() {
                @Override
                public Article call() throws Exception {
                    Article article = new Article();
                    article.setTitle(dto.getTitle());
                    System.out.println("run load");
                    return article;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new BizException("error", e);
        }
    }


    private class UserTemp {
        private String name;
        private Integer age;

        public UserTemp(String name, Integer age) {
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
            return "UserTemp{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


    @Test
    public void temp() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 100);
        map.put("a", 200);//一般的map key相同的情况下，会覆盖前面的值
        System.out.println(map);

    }
}
