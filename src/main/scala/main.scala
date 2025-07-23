package howdy

import httplib.all.*
import scala.scalanative.unsafe.*
import upickle.default.*
import httplib.enumerations.Code.BAD_REQUEST
import scala.collection.mutable.ArrayBuffer

case class Curtain(width: Int, height: Int) derives Writer

// This has to be globally accessible!
// Otherwise we can't use this variable from inside C function pointers
val z = Zone.open()
val stock = ArrayBuffer.empty[Curtain]

def rectangle_fits_scala(
    curtWidth: Int,
    curtHeight: Int,
    width: Int,
    height: Int
): Boolean =
  curtWidth >= width && curtHeight >= height

@extern def rectangle_fits_asm(
    curtWidth: Int,
    curtHeight: Int,
    width: Int,
    height: Int
): Boolean = extern

@main def hello =
  given Zone = z
  val handlers = Handlers()

  stock += Curtain(100, 240)

  (!handlers).list = () =>
    given Zone = z
    Resp(Code.OK, BodyType.JSON, toCString(write(stock)))

  (!handlers).create = (width: Int, height: Int) =>
    given Zone = z
    import util.boundary

    boundary:
      stock.zipWithIndex.foreach: (curtain, idx) =>
        if rectangle_fits_asm(
            curtain.width,
            curtain.height,
            width,
            height
          )
        then
          stock(idx) = Curtain(curtain.width - width, curtain.height - height)
          boundary.break(Resp(Code.OK, BodyType.NONE, null))
      Resp(Code.BAD_REQUEST, BodyType.TEXT, c"Order doesn't fit")

  start_server(handlers, c"localhost", 8899)
end hello
