class UserComparable extends User implements Comparable<UserComparable> {
    public UserComparable(int id, String name){
        super(id,name);
    }

    @Override
    public int compareTo(UserComparable o) {
        int idCompare = Integer.compare(this.getId(),o.getId());
        return idCompare;
    }
}
