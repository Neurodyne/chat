//package monoid

trait Monoid[A] {
  def mappend (a1:A, a2:A): A 
  def mzero: A
}


object Monoid {
  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    def mappend (a:Int, b:Int):Int = a + b 
    def mzero: Int = 0
  }
}


object run extends App {

  def sum[A:Monoid](xs:List[A]):A = {
    val m  = implicitly[Monoid[A]]
    xs.foldLeft(m.mzero)(m.mappend)
  }
  
  val res = sum(List(1,2,3,4))
  println (s"$res")

  (1 to 5) foreach { clk =>
    println (s"Tick")
  }
  
}


