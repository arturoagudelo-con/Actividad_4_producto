public class comida extends Producto{
    public comida(String nombre, double precio){
        super(nombre,precio);
    }

    @Override
    public String obtenerDetalleTipo(){
        return "Categoria: comida";
    }
}
