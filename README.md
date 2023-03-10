# cronometro_persistente
This is an object oriented approach to a timer, multi-threading and saving a persistent state to resume in the future.

The saved state is stored in "tiempo.dat" file as a object output stream. The hr,min,sec atributes in the database are objects atributes from the parent class Cronometro.

The menu allows you to Cargar (Load previous state), Guardar(Save the currect state) and Salir(Exit).
