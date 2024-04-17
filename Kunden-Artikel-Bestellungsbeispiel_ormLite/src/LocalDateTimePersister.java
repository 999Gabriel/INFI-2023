import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import org.joda.time.LocalDateTime;

import java.sql.SQLException;
import java.sql.Timestamp;

public class LocalDateTimePersister extends BaseDataType
{
    private static final LocalDateTimePersister singleTon = new LocalDateTimePersister();

    public static LocalDateTimePersister getSingleton()
    {
        return singleTon;
    }

    private LocalDateTimePersister()
    {
        // Tell ORMLite to treat this as a SQL Timestamp type
        super(SqlType.DATE, new Class<?>[]{LocalDateTime.class});
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr)
    {
        return Timestamp.valueOf(defaultStr).toLocalDateTime();
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject)
    {
        LocalDateTime localDateTime = (LocalDateTime) javaObject;
        return Timestamp.valueOf(String.valueOf(localDateTime));
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException
    {
        return results.getTimestamp(columnPos);
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos)
    {
        Timestamp timestamp = (Timestamp) sqlArg;
        return timestamp.toLocalDateTime();
    }
}
