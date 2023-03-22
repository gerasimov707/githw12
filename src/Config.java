public class Config {
        private Load load;
        private Save save;
        private Log log;

        public Config(Load load, Save save, Log log) {
            this.load = load;
            this.save = save;
            this.log = log;
        }

        public Load getLoad() {
            return load;
        }

        public Save getSave() {
            return save;
        }

        public Log getLog() {
            return log;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "load=" + load +
                    ", save=" + save +
                    ", log=" + log +
                    '}';
        }

        public static class Load {
            private boolean enabled;
            private String fileName;
            private String format;

            public Load(boolean enabled, String fileName, String format) {
                this.enabled = enabled;
                this.fileName = fileName;
                this.format = format;
            }

            public boolean isEnabled() {
                return enabled;
            }

            public String getFileName() {
                return fileName;
            }

            public String getFormat() {
                return format;
            }

            @Override
            public String toString() {
                return "Load{" +
                        "enabled=" + enabled +
                        ", fileName='" + fileName + '\'' +
                        ", format='" + format + '\'' +
                        '}';
            }
        }

        public static class Save {
            private boolean enabled;
            private String fileName;
            private String format;

            public Save(boolean enabled, String fileName, String format) {
                this.enabled = enabled;
                this.fileName = fileName;
                this.format = format;
            }

            public boolean isEnabled() {
                return enabled;
            }

            public String getFileName() {
                return fileName;
            }

            public String getFormat() {
                return format;
            }

            @Override
            public String toString() {
                return "Save{" +
                        "enabled=" + enabled +
                        ", fileName='" + fileName + '\'' +
                        ", format='" + format + '\'' +
                        '}';
            }
        }
    public static class Log {
        private boolean enabled;
        private String fileName;

        public Log(boolean enabled, String fileName) {
            this.enabled = enabled;
            this.fileName = fileName;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public String getFileName() {
            return fileName;
        }

        @Override
        public String toString() {
            return "Log{" +
                    "enabled=" + enabled +
                    ", fileName='" + fileName + '\'' +
                    '}';
        }
    }
}

