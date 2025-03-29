package br.com.ada.t1322.tecnicasprogramacao.projeto.view.command.task_sorting;

public enum SortingTaskOption {
    DEADLINE(1, "Data Limite", "deadline"),
    TITLE(2, "Título", "title"),
    STATUS(3, "Status", "status"),
    ID(4, "Id", "id"),
    NONE(5, "Sem ordenação", "");

    private final int option;
    private final String displayName;
    private final String criteria;

    SortingTaskOption(int option, String displayName, String criteria) {
        this.option = option;
        this.displayName = displayName;
        this.criteria = criteria;
    }

    public int getOption() {
        return option;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCriteria() {
        return criteria;
    }

    public static SortingTaskOption fromOption(int option) {
        for (SortingTaskOption sortingOption : values()) {
            if (sortingOption.option == option) {
                return sortingOption;
            }
        }
        return SortingTaskOption.NONE;
    }
}
