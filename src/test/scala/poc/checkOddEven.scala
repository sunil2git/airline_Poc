package poc
import org.scalatest._
import org.scalatest.FunSuite

import org.scalatest.junit.AssertionsForJUnit
import poc.airLinePocData



class checkOddEven extends FunSuite  {

  test("airLinePocData.oddEvenCheck") {
    assert(airLinePocData.oddEvenCheck(2) === false)
  }


}
