import java.util.*;
import java.util.stream.Collectors;

public class Person {

    private int age;
    private String name;
    private String country;

    public Person(int age, String name, String country) {
        this.age = age;
        this.name = name;
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public static void main(String[] args) {

        List<Person> people = Arrays.asList(
                new Person(20, "John", "USA"),
                new Person(35, "Sam", "Italy"),
                new Person(15, "Jamie", "England"),
                new Person(30, "Robert", "Italy"),
                new Person(20, "James", "Ireland"),
                new Person(25, "Peter", "USA"),
                new Person(5, "Jessica", "Norway"),
                new Person(40, "Roger", "Netherlands"),
                new Person(50, "Jim", "USA")
        );
        //1st
        System.out.print("Average Age Of Person is : ");
        averageOfPeople(people);

        System.out.println("");
        //2nd
        System.out.println("Filtered List on basis of Age and Vowel Check : ");
        List<Person> filteredList = filterPerson(people);
        filteredList.forEach(System.out::println);

        System.out.println("");
        //3rd
        System.out.println("Sorted Using Name and then Age(desc) : ");
        List<Person> SortedList = sortOfPerson(people);
        SortedList.forEach(System.out::println);

        System.out.println("");
        //4th Based on country gives Count
        Map<String, Long> countCountry = groupBasedonCountry(people);
        countCountry.forEach( (k,v) -> System.out.println("Country is :"+k+" Count is "+v));

        System.out.println("");
        //5th Based Country Get Age as Average
        Map<String ,Double> CountryAgeAvg = groupByCountryandAge(people);
        CountryAgeAvg.forEach( (k,v) -> System.out.println("Country is :"+k+" Average Age is "+v));

        //6th Based on Country Get max age
        Map<String, Optional<Person>> CountryMaxAge = groupByCountryOldest(people);
        CountryMaxAge.forEach( (k,v) -> System.out.println("Country is "+ k +" oldest age is "+
                v.get().getName()) );

        //7th Max Age is
        System.out.println("");
        System.out.println("Oldest age is "+ oldestPerson(people));

        //8th  Create a list of 20 random integers from (0-1000)
        System.out.println("");
        System.out.print("Random Integers are in between Range of 0 to 1000");
        Random r = new Random();
        r.ints(20,0,1001)
                .forEach(System.out::println);
    }

    //1st
    public static void averageOfPeople(List<Person> p)
    {
        OptionalDouble y =p.stream()
                .mapToInt((x) -> x.getAge())
                .average();

        double z = y.isPresent()? y.getAsDouble():0;

        System.out.println("Average age is : "+z);
    }

    //2nd
    public static List<Person> filterPerson(List<Person> p)
    {

        return p.stream().filter( f -> ( f.getAge() > 20 || checkVowels(f.getName()) ) )
                .collect(Collectors.toList());

    }

    public static boolean checkVowels(String k)
    {
        List<Character> al = Arrays.asList('a','e','i','o','u','A','E','I','O','U');
        for(int i=0;i<k.length();i++)
            if(al.contains(k.charAt(i))==true)
                return true;
            return false;
    }

    //3rd Sorting based on  Age if same then to check age in desc
    public static List<Person> sortOfPerson(List<Person> p)
    {
        return p.stream()
                .sorted(Comparator.comparing(Person :: getAge)
                .thenComparing(Person :: getName,Comparator.reverseOrder()))
                //.map(Person -> Person.getName())
                .collect(Collectors.toList());
    }

    //4th
    public static Map<String,Long> groupBasedonCountry(List<Person> p)
    {
        return p.stream()
                .collect(Collectors.groupingBy
                        ( Person::getCountry,Collectors.counting() ));
    }

    //5th
    public static Map<String,Double> groupByCountryandAge(List<Person> p)
    {
        return p.stream()
                .collect(Collectors.groupingBy(Person::getCountry,
                        Collectors.averagingDouble(Person::getAge) ));
    }

    //6th
    public static Map<String, Optional<Person>> groupByCountryOldest(List<Person> p)
    {
        return p.stream()
                .collect(Collectors.groupingBy
                        (Person::getCountry, Collectors.maxBy(Comparator.comparing(Person::getAge))));
    }

    //7th
    public static String oldestPerson(List<Person> p)
    {
        Optional<String> optionalName =  p.stream()
                .max(Comparator.comparing(Person::getAge))
                .map(person -> person.getName());
        if(optionalName.isPresent())
            return optionalName.get();
        else
            return "NOT EXIST";
    }

}
