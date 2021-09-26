package mx.gob.sat.siat.juridica.rrl.util.constante;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Runtime {
    private static Runtime instance;
    private Environment environment;

    private Runtime() {}

    public static Runtime getInstance() {

        if (instance == null) {
            instance =
                    (Runtime) new ClassPathXmlApplicationContext("classpath*:/applicationContext/rrl/rrl-context.xml")
                            .getBean("runtime");
        }

        return instance;
    }

    public enum Environment {
        PRODUCCION("produccion"), ESTRES("estres"), UAT("uat"), DESARROLLO("desarrollo"), LOCAL("local");

        private String desc;

        private Environment(String desc) {
            this.desc = desc;
        }

        public String toString() {
            return desc;
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public boolean isFielRequired() {
        boolean result;

        switch (environment) {
        case ESTRES:
            result = false;
            break;
        default:
            result = true;
            break;
        }

        return result;
    }
}
