import java.util.Objects;

class User {
    private Integer id;
    private String name;

    public User(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj==null || getClass()!=obj.getClass()){
            return false;
        }

        User other = (User)obj;
        return Objects.equals(id, other.id);

        }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return "User{id="+id+",name="+name+"}";
    }
}
