import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.Scanner;

public class Main {
    static List<Empleado> empleados;

    public static void main(String[] args) throws IOException {
        cargarArchivo();
        Scanner sc=new Scanner(System.in);
        boolean salir=false;
        int opcion;

        while(!salir){
            System.out.println("Bienvenido a nuestro sistema de consultas...");
            System.out.println("Hecho por: Juan Fernando Romero y Verónica Zapata :)\n");
            System.out.println("1. Listar empleados");
            System.out.println("2. Empleados en un rango determinado");
            System.out.println("3. Coleccion de empleados por departamento");
            System.out.println("4. Cantidad de empleados por departamento");
            System.out.println("5. Sumatoria de la nomina por departamento");
            System.out.println("6. Nombre del empleado que gana mas por departamento");
            System.out.println("7. Nombre del empleado que más gana en general");
            System.out.println("8. Nombre del empleado que menos gana en general");
            System.out.println("9. Promedio del salario por departamento");
            System.out.println("10. Valor promedio general");
            System.out.println("11. Valor total de la nomina");
            System.out.println("12. Salir");

            System.out.println("Ingrese una opcion: ");
            opcion=sc.nextInt();

            switch(opcion){
                case 1:
                    ordApe();
                    System.out.println(" ");
                    break;
                case 2:
                    ranGen();
                    System.out.println(" ");
                    break;
                case 3:
                    empDep();
                    System.out.println(" ");
                    break;
                case 4:
                    cantEmpDep();
                    System.out.println(" ");
                    break;
                case 5:
                    sumNomina();
                    System.out.println(" ");
                    break;
                case 6:
                    nomSalMayor();
                    System.out.println(" ");
                    break;
                case 7:
                    nomMay();
                    System.out.println(" ");
                    break;
                case 8:
                    nomMin();
                    System.out.println(" ");
                    break;
                case 9:
                    promDep();
                    System.out.println(" ");
                    break;
                case 10:
                    promSalT();
                    System.out.println(" ");
                    break;
                case 11:
                    TotalNom();
                    System.out.println(" ");
                    break;
                case 12:
                    System.out.println("Gracias por usar nuestro sistema...");
                    salir=true;
                    break;
                default:
                    System.out.println("Solo números entre 1 y 12");
            }
        }
         
    }

    //Cargar archivo .csv
    public static void cargarArchivo() throws IOException{
        Pattern pattern=Pattern.compile(";");
        String fileName="empleado.csv";

        try(Stream<String> lines=Files.lines(Path.of(fileName))) {
            empleados = lines.map(line -> {
                String[] arr = pattern.split(line);
                return new Empleado(arr[0], arr[1], arr[2], Double.parseDouble(arr[3]), arr[4]);
            }).collect(Collectors.toList());
            //empleados.forEach(System.out::println);
        }
    }

    //Funciones que obtienen primer nombre y apellido del empleado
    static Function<Empleado, String> porPrimerNombre = Empleado::getPrimerNombre;
    static Function<Empleado, String> porApellidoPaterno = Empleado::getApellidoPaterno;

    //Ordenar empleados por apellido y nombre
    static Comparator<Empleado> apellidoLuegoNombre=Comparator.comparing(porApellidoPaterno).thenComparing(porPrimerNombre);


    //Imprimir empleados de manera ordenada usando el Comparator anterior
    public static void ordApe(){
        System.out.println("\t\t  ID \t  |Nombre|Apellido|Salario|Departamento");
        empleados.stream().sorted(apellidoLuegoNombre).forEach(System.out::println);
    }

    //Salario entre 4000 y 6000, "busca" los valores True de la condición
    static Predicate<Empleado> cuatroAseisMil=e-> (e.getSalario()>=4000 && e.getSalario()<=6000);

    //"Imprime" o los resultados True del predicate, al mismo tiempo los ordena ASC por salario
    public static void ranGen(){
        System.out.println("");
        System.out.println("Empleados con salario entre 4000 y 6000: ");
        System.out.println("");
        System.out.println("ID |Nombre|Apellido|Salario|Departamento");
        empleados.stream().filter(cuatroAseisMil).sorted(Comparator.comparing(Empleado::getSalario)).forEach(empleado->System.out.println(empleado.getId()+","+empleado.getPrimerNombre()+","+empleado.getApellidoPaterno()+","+empleado.getSalario()+","+empleado.getDepartamento()));
    }

    //Empleados por departamento
    public static void empDep(){
        System.out.println("");
        System.out.println("Empleados por departamento: ");
        System.out.println("");
        Map<String, List<Empleado>> empDep = empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento));
        empDep.forEach((dep,emp)->{
                System.out.println(dep);
                System.out.println(" ");
                System.out.println("Nombre|Apellido|Salario");
                emp.forEach(empleado->System.out.println(empleado.getPrimerNombre()+","+empleado.getApellidoPaterno()+","+empleado.getSalario()));
                System.out.println(" ");
            }
        );
    }
        

    //Cantidad empleados por departamento
    public static void cantEmpDep(){
        System.out.println("");
        System.out.println("Cantidad de empleados por departamento: ");
        System.out.println("");
        Map<String, Long> cantEmpDep = empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento,TreeMap::new,Collectors.counting()));
        cantEmpDep.forEach((dep,cont)->{
                System.out.println("El "+dep + " tiene " + cont+" empleados.");
            }
        );
    }

    //Sumatoria nomina de sueldos por departamento
    public static void sumNomina(){
        System.out.println("");
        System.out.println("Sumatoria nomina de sueldos por departamento: ");
        System.out.println("");
        Map<String, Double> sumNomina = empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento,TreeMap::new,Collectors.summingDouble(Empleado::getSalario)));
        sumNomina.forEach((dep,sum)->{
                System.out.println("El "+dep + " tiene una nomina de " + sum+" pesos.");
            }
        );
    }

    //Nombre del empleado con mayor salario por departamento
    public static void nomSalMayor(){
        System.out.println("");
        System.out.println("Nombre del empleado con mayor salario por departamento: ");
        System.out.println("");
        Map<String, Empleado> nomSalMayor=empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento,TreeMap::new,Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Empleado::getSalario)), e->e.get())));
        nomSalMayor.forEach((dep,emp)->{
                    System.out.println("El "+dep + " tiene como empleado con mayor salario a " + emp.getPrimerNombre()+" "+emp.getApellidoPaterno()+" ganando: "+emp.getSalario()+" pesos.");
                }
        );
    }

    //Nombre del empleado que mayor salario recibe
    public static void nomMay(){
        Empleado salmax=empleados.stream().max(Comparator.comparingDouble(Empleado::getSalario)).get();
        System.out.println("Empleado con el mayor salario es "+salmax.getPrimerNombre()+" "+salmax.getApellidoPaterno()+" con un salario de "+salmax.getSalario()+" pesos.");

    }

    //Nombre del empleado que menor salario recibe
    public static void nomMin(){
        Empleado salmin=empleados.stream().min(Comparator.comparingDouble(Empleado::getSalario)).get();
        System.out.println("Empleado con el menor salario es "+salmin.getPrimerNombre()+" "+salmin.getApellidoPaterno()+" con un salario de "+salmin.getSalario()+" pesos.");
    }

    //Promedio de salario por departamento
    public static void promDep(){
        System.out.println("");
        System.out.println("Promedios por departamento: ");
        System.out.println("");
        Map<String,Double> promDep=empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento,TreeMap::new,Collectors.averagingDouble(Empleado::getSalario)));
        promDep.forEach((dep,prom)->{
            System.out.println("El "+dep+" tiene como promedio de salarios "+prom+" pesos.");
        }
        );
    }

    //Promedio salario general
    public static void promSalT(){
        System.out.println("");
        System.out.println("Promedio del salario general: "+empleados.stream().mapToDouble(Empleado::getSalario).average().getAsDouble()+" pesos.");
    }

    //Valor total de la nómina
    public static void TotalNom(){
        System.out.println("");
        System.out.println("Valor total de la nómina: "+empleados.stream().mapToDouble(Empleado::getSalario).sum()+" pesos.");;
    }

}
