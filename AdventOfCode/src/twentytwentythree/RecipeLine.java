package twentytwentythree;

public class RecipeLine {
    private String number;
    private String pattern;
    private String uptake;
    private String group;
    private String head;
    private String section;
    private String product;
    private String recall;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public Integer getNumberAsInt() {
        return Integer.valueOf(number);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getUptake() {
        return uptake;
    }

    public void setUptake(String uptake) {
        this.uptake = uptake;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }
    
    public RecipeLine getCopy(){
        RecipeLine recipeLine = new RecipeLine.RecipeLineBuilder()
                .withNumber(new String(this.getNumber()))
                .withHead(new String(this.getHead()))
                .withSection(new String(this.getSection()))
                .withProduct(new String(this.getProduct()))
                .withPattern(new String(this.getPattern()))
                .withUptake(new String(this.getUptake()))
                .withGroup(new String(this.getGroup()))
                .withRecall(new String(this.getRecall()))
                .build();
        return recipeLine;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(number + " ");
        sb.append(pattern + " ");
        sb.append(uptake + " ");
        sb.append(group + " ");
        sb.append(head + " ");
        sb.append(section + " ");
        sb.append(product + " ");
        sb.append(recall + " ");

        return sb.toString();
    }

    public String[] getHeader() {
        String[] arr = new String[8];
        arr[0] = "Number";
        arr[1] = "Pattern";
        arr[2] = "Uptake";
        arr[3] = "Group";
        arr[4] = "Head";
        arr[5] = "Section";
        arr[6] = "Product";
        arr[7] = "Recall";
        return arr;
    }
    
    public String[] toArray() {
        String[] arr = new String[8];
        arr[0] = number;
        arr[1] = pattern;
        arr[2] = uptake;
        arr[3] = group;
        arr[4] = head;
        arr[5] = section;
        arr[6] = product;
        arr[7] = recall;
        return arr;
    }

    public static class RecipeLineBuilder {
        private String number;
        private String pattern;
        private String uptake;
        private String group;
        private String head;
        private String section;
        private String product;
        private String recall;

        public RecipeLineBuilder withNumber(String number) {
            this.number = number;
            return this;
        }
        
        public RecipeLineBuilder withPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public RecipeLineBuilder withUptake(String uptake) {
            this.uptake = uptake;
            return this;
        }

        public RecipeLineBuilder withGroup(String group) {
            this.group = group;
            return this;
        }
        public RecipeLineBuilder withHead(String head) {
            this.head = head;
            return this;
        }

        public RecipeLineBuilder withSection(String section) {
            this.section = section;
            return this;
        }

        public RecipeLineBuilder withProduct(String product) {
            this.product = product;
            return this;
        }
        public RecipeLineBuilder withRecall(String recall) {
            this.recall = recall;
            return this;
        }
        public RecipeLine build() {
            RecipeLine recipeLine = new RecipeLine();
            recipeLine.setNumber(number);
            recipeLine.setPattern(pattern);
            recipeLine.setUptake(uptake);
            recipeLine.setGroup(group);
            recipeLine.setHead(head);
            recipeLine.setSection(section);
            recipeLine.setProduct(product);
            recipeLine.setRecall(recall);
            return recipeLine;
        }
    }

}
