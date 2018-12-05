package ru.otus.hw11hibernate.hibernate.config;

import org.hibernate.cfg.Configuration;
import ru.otus.hw11hibernate.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.UserDataSetHibernate;

public class ConfigurationHibernate {
    private Configuration configuration;
    public ConfigurationHibernate() {
        configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSetHibernate.class);
        configuration.addAnnotatedClass(AddressDataSetHibernate.class);
        configuration.addAnnotatedClass(PhoneDataSetHibernate.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:~/test");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "sa");

        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.generate_statistics", "false");
        configuration.setProperty("hibernate.use_sql_comments", "false");


        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
    }

        public Configuration getConfiguration() {
            return this.configuration;
        }
}
