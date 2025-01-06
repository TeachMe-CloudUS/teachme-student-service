package us.cloud.teachme.studentservice.infrastructure.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.NonNull;
import us.cloud.teachme.studentservice.infrastructure.persistance.converter.PhoneNumberReadingConverter;
import us.cloud.teachme.studentservice.infrastructure.persistance.converter.PhoneNumberWritingConverter;
import us.cloud.teachme.studentservice.infrastructure.persistance.converter.UserIdReadingConverter;
import us.cloud.teachme.studentservice.infrastructure.persistance.converter.UserIdWritingConverter;

@Configuration
@Profile("!test")
class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    @NonNull
    public MongoClient mongoClient() {
        return MongoClients.create(uri);
    }

    @Override
    @NonNull
    public String getDatabaseName() {
        return database;
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter adapter) {
        adapter.registerConverter(new UserIdReadingConverter());
        adapter.registerConverter(new UserIdWritingConverter());
        adapter.registerConverter(new PhoneNumberReadingConverter());
        adapter.registerConverter(new PhoneNumberWritingConverter());
    }
}
