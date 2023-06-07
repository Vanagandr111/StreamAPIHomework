import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
  public static Logger LOGGER = Logger.getAnonymousLogger();

  public static void main(String[] args) {

    List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
    List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
    Collection<Person> persons = new ArrayList<>();
    for (int i = 0; i < 10_000_000; i++) {
      persons.add(new Person(
        names.get(new Random().nextInt(names.size())),
        families.get(new Random().nextInt(families.size())),
        new Random().nextInt(100),
        Sex.values()[new Random().nextInt(Sex.values().length)],
        Education.values()[new Random().nextInt(Education.values().length)])
      );
    }

    //Найти количество несовершеннолетних (т.е. людей младше 18 лет).
    long underagePeopleCount = persons.stream()
      .filter(person -> person.getAge() < 18).count();

    //Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
    List<String> recruitPeopleSurnames = persons.stream()
      .filter(person -> person.getSex().equals(Sex.MAN))
      .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
      .map(Person::getFamily)
      .collect(Collectors.toList());

    //Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
    // (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
    List<Person> sortedPeopleAbleToWork = persons.stream()
      .filter(person -> person.getAge() >= 18)
      .filter(person -> person.getEducation().equals(Education.HIGHER))
      .filter(person -> person.getSex().equals(Sex.MAN) ? person.getAge() < 65 : person.getAge() < 60)
      .sorted(Comparator.comparing(Person::getFamily))
      .collect(Collectors.toList());

    LOGGER.info("Количество несовершеннолетних людей: " + underagePeopleCount);
    LOGGER.info("Список фамилий призывников: " + recruitPeopleSurnames);
    LOGGER.info("Отсортированный список работоспособных людей по фамилии: " + sortedPeopleAbleToWork);
  }
}