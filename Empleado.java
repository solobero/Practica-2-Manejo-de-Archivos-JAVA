public class Empleado {

    private String id;
    private String primerNombre;
    private String apellidoPaterno;
    private double salario;
    private String departamento;

    public Empleado(String id,String primerNombre, String apellidoPaterno, double salario, String departamento) {
        this.id = id;
        this.primerNombre = primerNombre;
        this.apellidoPaterno = apellidoPaterno;
        this.salario = salario;
        this.departamento = departamento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Empleado |" +
                "id " + id +
                "-> "+ primerNombre +
                " " + apellidoPaterno +
                " " + salario +
                " " + departamento  +
                '|';
    }
}
