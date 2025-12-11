import java.util.*;
import java.util.stream.Collectors;

public class CourseFlatten {
    static class Student{
        private String id;
        private String name;

        public Student(String id, String name){
            this.id = id;
            this.name = name;
        }

        public String getId(){
            return id;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getName(){
            return name;
        }

        public void setName(String name){
            this.name = name;
        }
    }

    static class Course{
        private String code;
        private String name;

        public Course(String code, String name){
            this.code = code;
            this.name = name;
        }

        @Override
        public boolean equals(Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Course course = (Course) o;
            return code.equals(course.code);
        }

        @Override
        public int hashCode(){
            return Objects.hashCode(code);
        }

        @Override
        public String toString(){
            return code+":"+name;
        }

        public String getCode(){
            return code;
        }

        public void setCode(String code){
            this.code = code;
        }

        public String getName(){
            return name;
        }

        public void setName(String name){
            this.name = name;
        }
    }

    public static Set<Course> deduplicatedCourse(Map<Student, List<Course>>stuCourses){
        return stuCourses.values().stream().flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        Student s1 = new Student("s001","张三");
        Student s2 = new Student("s002","李四");
        Student s3 = new Student("s003","王五");

        Course c1 = new Course("MA101","高等数学");
        Course c2 = new Course("MA201","微积分");
        Course c3 = new Course("PR101","数字电路");
        Course c4 = new Course("CO303","大学英语");

        Map<Student, List<Course>>stuCourses = new HashMap<>();
        stuCourses.put(s1, Arrays.asList(c1,c3,c4));
        stuCourses.put(s2, Arrays.asList(c2,c4));
        stuCourses.put(s3, Arrays.asList(c1,c4));

        System.out.println("===去重后的学生课程===");
        Set<Course> dedupCourses = deduplicatedCourse(stuCourses);
        dedupCourses.forEach(System.out::println);
    }
}
