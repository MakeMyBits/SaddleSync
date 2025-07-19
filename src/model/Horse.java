package model;

public class Horse {
	
    private int id;
    private String name;
    private String breed;
    private int age;
    private String box;
    private String gender;
    private String comments;
    private String image;
    
    public Horse() {}
    
    // Konstruktor med ID (för databasanrop)
    public Horse(int id, String name, 
    	String breed, int age,
    	String box, String gender,
    	String comments) {
        
    	this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.box = box;
        this.gender = gender;
        this.comments = comments;
        
    }

    // Konstruktor utan ID (för nya hästar innan de fått ett ID från databasen)
    public Horse(String name, 
    	String breed, int age, 
    	String box,String gender,
    	String comments) {
        
    	this.name = name;
        this.breed = breed;
        this.age = age;
        this.box = box;
        this.gender = gender;
        this.comments = comments;
        
    }
    
 // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public void setBox(String box) {
    	
    	this.box = box;
    	
    }
    
    public void setGender(String gender) {
    	
    	this.gender = gender;
    	
    }
    
    public void setComments(String comments) {
    	
    	this.comments = comments;
    	
    }
    
    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }
    
    public String getBox() { 
    	return box;
    }
    
    public String getGender() {
    	
    	return gender;
    	
    }    
    
    public String getComments() {
    	
    	return comments;
    	
    }
    
    // För debug-utskrift
    @Override
    public String toString() {
        return name + " (" + breed + ", " + age + " år)";
    }
}
