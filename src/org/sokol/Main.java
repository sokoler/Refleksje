package org.sokol;

import java.lang.annotation.*;
import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) {

        UseCollaborator useCollaborator = new UseCollaborator();
        System.out.println(useCollaborator);
        try {
            Kontener.wstrzyknij(useCollaborator, "Wstrzyknięty collaborator");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(useCollaborator);


    }
}

// TODO 1 własna adnotacja
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Wstrzyknij{


}

// TODO 2 klasa obiektu do wstrzykiwania

class Collaborator{

    private String name;

    public Collaborator(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Collaborator{" +
                "name='" + name + '\'' +
                '}';
    }
}

// TODO 3 Klasa z polami typu Collaborator

class UseCollaborator{

    @Wstrzyknij
    private Collaborator collaboratorWithAnnotation;

    private Collaborator collaborator;

    @Override
    public String toString() {
        return "UseCollaborator{" +
                "collaboratorWithAnnotation=" + collaboratorWithAnnotation +
                ", collaborator=" + collaborator +
                '}';
    }
}

class Kontener{

    static void wstrzyknij(Object target, String name) throws IllegalAccessException{

        //TODO 4 pobierz pola obiektu i przeliteruj po nich

        Field[] declaredFields = target.getClass().getDeclaredFields();

            for(Field field : declaredFields) {
                //TODO 5 pobierz adnotacje pola i przeliteruj po nich

                Annotation [] annotations = field.getAnnotations();
                    for(Annotation annotation : annotations){

                        //TODO 6 Utwórz instancje Collaborator i przypisz do pola z adnotacja Wstrzyknij

                        if( "org.sokol.Wstrzyknij".equals(annotation.annotationType().getName())){

                            field.setAccessible(true);

                            field.set(target, new Collaborator(name));

                        }

                    }


            }



    }

}