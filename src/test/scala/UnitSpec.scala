import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

abstract class UnitSpec() extends AnyFlatSpec with GivenWhenThen with Matchers with TableDrivenPropertyChecks with BeforeAndAfterAll with BeforeAndAfterEach {
}
