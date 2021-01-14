package db.migration;

import io.sitoolkit.csv.core.CsvLoader;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

@SuppressWarnings("squid:S101")
public class V2_1_0__AddRecords extends BaseJavaMigration {

  private final Log log = LogFactory.getLog(V2_1_0__AddRecords.class);

  @Override
  public void migrate(Context context) throws Exception {
    CsvLoader.load(context.getConnection(), getClass(), log::info);
  }
}