package com.workintech.rest.controller;

import com.workintech.rest.entity.Animal;
import com.workintech.rest.mapping.AnimalResponse;
import com.workintech.rest.validation.AnimalValidation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @ işareti ile tag'lediğimiz değerlere annotation diyoruz.

// @RestController -> Rest Api için yapıyoruz. Controller olarak iş yapıcak...
// @Value -> application.properties dosyasından variable çekebilmek için. Kullanım örneği aşağıda var. application.properties dosyasına secret key'ler konmaz. Statik projede değişmez veriler konulabilir.
// @GetMapping("/welcome") -> endpoint hazırlar, Get + Path = Endpoint
// @PostConstruct -> Constructor metodumdan sonra çalışacak ilk metod olması için işaretliyorum. Ama her zaman constructor çalışır. Asla karıştırma. Bir defa çalışır. UseEffect(()=>{},[]) didmount anı gibi düşün.
// @PreDestroy -> @PostConstruct'ın kardeşidir. Garbage Collector uygulama bittiğinde tüm verileri siler, silmeden önce son bu metodumuz çalışır. Çıkıştan bir önceki metod.
// @PathVariable -> id değerini alırken kullanırız.
// @RequestBody -> Gelen değeri Request Body'den aldığımızı anlatıyoruz.
// @RequestMapping -> Bunu yaptığımızda otomatik olarak aşağıda yazdığımız tüm endpointlerimize bunu bekler. Otomatik olarak ekler. Tek tek elle yazmak zorunda kalmayız.
// Tüm bu req ve res JSON conversion'larını JSON library yönetir. Eskiden bunun için bir çok configürasyon yazılıyordu. Artık buna gerek yok. Hem girişte hem çıkışta bizim için JSON convertor Library var.

@RestController
@RequestMapping("/workintech/animal")
public class AnimalController {
    @Value("${people.name}")
    private String name;

    @Value("${people.surname}")
    private String surname;

    private Map<Integer, Animal> animalMap; // = new HashMap(); -> [1] Kötü yöntem.


    /*
        public AnimalController() {
            this.animalMap = animalMap;                         -> [2] Alternatif Yöntem. Constructor'ında yazmak. Yapabilirsin. Ama bunun da alternatifi var.
        }
    */
    @PostConstruct
    public void initialMethod() {//                             -> [3] Yöntem. Constructor sonrası sıraya alıyoruz. Hemen çalışıyor.
        animalMap = new HashMap<>();
    }

    @GetMapping("/welcome")
    public String sayHello() {
        return name + " " + surname + " welcome to Spring Boot 🤌";
    }

    @GetMapping("/")
    public List<Animal> getAllAnimals() {
        return animalMap.values().stream().toList(); // animal Map'teki verilerin value'larını al, stream'e çevir, listeye çevir
    }

    @GetMapping("/{id}")
    public AnimalResponse getAnimal(@PathVariable int id) {
        if (!AnimalValidation.isIdValid(id)) {
            return new AnimalResponse(null, "Id is not valid...", 400);
        }
        if (!AnimalValidation.containsKey(animalMap, id)) {
            return new AnimalResponse(null, "Id is not in the list...", 404);
        }
        return new AnimalResponse(animalMap.get(id), "Animal founded.", 200); // message ve status ekleyebilmek için Mapping klasörü altında bu sınıfı açtık.
    }

    @PostMapping("/")
    public AnimalResponse createAnimal(@RequestBody Animal animal) {
        if (AnimalValidation.containsKey(animalMap, animal.getId())) {
            // TODO animal already exist...
            return new AnimalResponse(null, "Id is already exist...", 404);
        }
        if (!AnimalValidation.isAnimalPropertiesValid(animal)) {
            //TODO animal properties are not valid!!!!...
            return new AnimalResponse(null, "Animal properties are not valid...", 404);

        }
        animalMap.put(animal.getId(), animal);
        return new AnimalResponse(animalMap.get(animal.getId()), "Animal created", 201);
    }

    @PutMapping("/{id}")
    public AnimalResponse updateAnimal(@PathVariable int id, @RequestBody Animal animal) {
        if (!AnimalValidation.containsKey(animalMap, id)) {
            // TODO animal is not exist
            return new AnimalResponse(null, "Animal is not exist...", 404);
        }
        if (!AnimalValidation.isAnimalPropertiesValid(animal)) {
            //TODO animal properties are not valid!!!!....
            return new AnimalResponse(null, "Animal properties are not valid...", 404);
        }
//        animalMap.put(id, animal); -> 1. Alternatif, ama varolanı değiştirmenin diğer yolu:
        animalMap.put(id, new Animal(id, animal.getName())); // 2. Alternatif.
        return new AnimalResponse(animalMap.get(id), "Animal updated", 200);
    }

    @DeleteMapping("/{id}")
    public AnimalResponse deleteAnimal(@PathVariable int id) {
        if(!AnimalValidation.containsKey(animalMap, id)) {
            //TODO Animal is not exist
            return new AnimalResponse(null, "Animal is not exist...", 404);
        }
        Animal founded = animalMap.get(id);
        animalMap.remove(id);
        return new AnimalResponse(founded, "Animal deleted...", 200);
    }

    @PreDestroy
    public void koprudenOncekiSonMetod() {
        System.out.println("Animal Controller Has Been Destroyed... Bye...");
    }

}
