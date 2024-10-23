# TP1 - Lasers

## Integrantes

- Bruno Kuznicki - Padrón: 111413 - GitHub: @brunokzz
- Camila Figueroa Cillo - Padrón: 11204 - GitHub: @camilaFigueroaCillo


## ACLARACIONES

## CENTROS
De las 9 coordenadas que componen a una celda la que está en el medio la llamamos centro. Los centros sirven para que, a partir de la última posición del láser, yo pueda saber en qué bloque estoy parado y en ese momento decidir entre avanzar, o no avanzar y bifurcar.
De esta manera, para la última posición en la que se ubica el láser se verifica la siguiente posición en x y en y. Allí tenemos dos posibles resultados:
Si tocó algún centro, significa que en la celda a la que llegué hay un bloque, y por lo tanto este debe bifurcar (o no) el láser.
Caso contrario (no se encontró centro) significa que en esa celda no hay bloque, lo que indica que el láser siga avanzando en la dirección que venía.

## COORDENADAS IMPARES DE UNA CELDA
De todas las coordenadas que posee una celda (9 en total), el láser sólo puede atravesar las impares. Como el laser siempre se mueve en una coordenada par y otra impar (por ejemplo, (0, 1) (2,1)), para dirigir el láser se necesita saber en cuál de las coordenadas impares de la celda cayó el laser, principalmente porque según la direccion en la que viene el laser y la coordenada impar en la que caiga, varía en hacia dónde se debe bifurcar/desviar el laser

## MOVIMIENTO DE BLOQUES
Anteriormente habiamos creado una interfaz BloqueDesplazable con un único método Desplazar(Celda celda), y esta interfaz era implementada sólo por los bloques que pueden moverse. Esto ocasionaba que, al momento de movilizar un bloque, se debía verificar que éste sea un BloqueDesplazable y en base a ese estado ejecutar o no el movimiento, lo que violaba el principio "Tell, Don't Ask". Para evitar esto, decidimos incluir en la clase Bloque (dentro del método Desplazar()) la decisión de mover o no la instancia de Bloque según su tipo.




## RELACIONES DE LA CLASE COORDENADA
Aunque varias clases usan como atributo a la clase Coordenada, las relaciones son las siguientes:

- Nivel - - - > Coordenada (dependencia, ya que Nivel crea la coordenada inicial para Objetivo y Laser, pero no las guarda como atributo)
- Celda ◆----> Coordenada (composición, ya que Celda crea las coordenadas que necesita y las guarda como atributo; por ende es el dueño)

Inicialmente pensamos pasar por parámetro la fila y la columna al constructor de Laser/Objetivo (además de cualquier clase que requiera de Coordenada) y que ellos mismos instancien esa clase en su constructor. De esta forma, se evitaba romper el Explicit Dependencies Principle instanciando a Coordenada desde algun metodo de Nivel.

Luego, surgió otro problema: De todas las clases que necesitaban Coordenada, había varias que tenían una relación de composición con ella (por ejemplo, Nivel y Celda), mientras que otras dependían de ella. Por lo tanto ¿Quién era el dueño de la clase Coordenada? ¿Laser, Objetivo, Celda o Nivel?

Debido a que una coordenada puede existir sin un laser o sin un objetivo, se optó por hacer que Nivel cree las coordenadas, y que Laser y Objetivo las agreguen en lugar de que las compongan. De esta manera, la unica clase que compone Coordenada es Celda.




## INTERFAZ Espejar
Tanto el BloqueVidrio como el BloqueEspejo necesitan espejar el láser, y para evitar herencias entre ellas se creó la interfaz Espejar que es implementada por ambas clases. De esta manera, se evita repetir código para clases que hacen la misma acción respecto al laser.
