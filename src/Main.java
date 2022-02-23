import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    private static List<Person> generateListPeople() {
        List<Person> persons = new ArrayList<>();

        List<String> names = Arrays.asList(
                "Noah", "Liam", "Mason", "Jacob", "William", "Ethan", "James", "Alexander", "Michael", "Benjamin",
                "Elijah", "Daniel", "Aiden", "Logan", "Matthew", "Lucas", "Jackson", "David", "Oliver", "Jayden",
                "Joseph", "Gabriel", "Samuel", "Carter", "Anthony", "John", "Dylan", "Luke", "Henry", "Andrew",
                "Isaac", "Christopher", "Joshua", "Wyatt", "Sebastian", "Owen", "Caleb", "Nathan", "Ryan", "Jack",
                "Hunter", "Levi", "Christian", "Jaxon", "Julian", "Landon", "Grayson", "Jonathan", "Isaiah", "Charles",
                "Emma", "Olivia", "Sophia", "Ava", "Isabella", "Mia", "Abigail", "Emily", "Charlotte", "Harper",
                "Madison", "Amelia", "Elizabeth", "Sofia", "Evelyn", "Avery", "Chloe", "Ella", "Grace", "Victoria",
                "Aubrey", "Scarlett", "Zoey", "Addison", "Lily", "Lillian", "Natalie", "Hannah", "Aria", "Layla",
                "Brooklyn", "Alexa", "Zoe", "Penelope", "Riley", "Leah", "Audrey", "Savannah", "Allison", "Samantha",
                "Nora", "Skylar", "Camila", "Anna", "Paisley", "Ariana", "Ellie", "Aaliyah", "Claire", "Violet");
        List<String> families = Arrays.asList(
                "Abramson", "Adamson", "Adderiy", "Addington", "Adrian", "Albertson", "Aldridge", "Allford", "Alsopp", "Anderson",
                "Andrews", "Archibald", "Arnold", "Arthurs", "Atcheson", "Attwood", "Audley", "Austin", "Ayrton", "Babcock",
                "Backer", "Baldwin", "Bargeman", "Barnes", "Barrington", "Bawerman", "Becker", "Benson", "Berrington", "Birch",
                "Bishop", "Black", "Blare", "Blomfield", "Boolman", "Bootman", "Bosworth", "Bradberry", "Bradshaw", "Brickman",
                "Brooks", "Brown", "Bush", "Calhoun", "Campbell", "Carey", "Carrington", "Carroll", "Carter", "Chandter",
                "Ralphs", "Ramacey", "Reynolds", "Richards", "Roberts", "Roger", "Russel", "Ryder", "Salisburry", "Salomon",
                "Samuels", "Saunder", "Shackley", "Sheldon", "Sherlock", "Shorter", "Simon", "Simpson", "Smith", "Stanley",
                "Stephen", "Stevenson", "Sykes", "Taft", "Taylor", "Thomson", "Thorndike", "Thornton", "Timmons", "Tracey",
                "Turner", "Vance", "Vaughan", "Wainwright", "Walkman", "Wallace", "Waller", "Walter", "Ward", "Warren",
                "Watson", "Wayne", "Webster", "Wesley", "White", "WifKinson", "Winter", "Wood", "Youmans", "Young");

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)]));
        }

        return persons;
    }

    private static <T> void printList(String title, List<T> list) {
        System.out.println(title);
        for (T obj : list) {
            System.out.println(obj);
        }
        System.out.println();
    }

    private static Predicate<Person> filterPerson(final Sex sex,
                                                  final int ageFrom,
                                                  final int ageTo,
                                                  final Education education) {
        return person -> person.getSex().equals(sex)
                && person.getAge() >= ageFrom
                && person.getAge() < ageTo
                && person.getEducation().equals(education);
    }

    public static void main(String[] args) {
        List<Person> persons = generateListPeople();

        long c = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();

        List<String> conscripts = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        List<Person> hardworkingPeople = persons.stream()
                .filter(filterPerson(Sex.MAN, 18, 65, Education.HIGHER)
                        .or(filterPerson(Sex.WOMAN, 18, 60, Education.HIGHER)))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        System.out.println("Количество несовершеннолетних: " + c);
        printList("Список фамилий призывников: ", conscripts);
        printList("Список работоспособных людей с высшим образованием, отсортированный по фамилии: ", hardworkingPeople);
    }
}