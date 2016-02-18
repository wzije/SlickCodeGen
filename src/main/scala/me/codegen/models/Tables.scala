package me.codegen.models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Action.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Action
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(32,true), Default(None) */
  case class ActionRow(id: Int, name: Option[String] = None)
  /** GetResult implicit for fetching ActionRow objects using plain SQL queries */
  implicit def GetResultActionRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[ActionRow] = GR{
    prs => import prs._
    ActionRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table action. Objects of this class serve as prototypes for rows in queries. */
  class Action(_tableTag: Tag) extends Table[ActionRow](_tableTag, "action") {
    def * = (id, name) <> (ActionRow.tupled, ActionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name).shaped.<>({r=>import r._; _1.map(_=> ActionRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(32,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(32,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Action */
  lazy val Action = new TableQuery(tag => new Action(tag))
}
