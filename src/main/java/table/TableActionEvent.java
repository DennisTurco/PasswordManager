package table;

/**
 *
 * @author Lorenzo
 */
public interface TableActionEvent {

    public void onEdit(int row);

    public void onDelete(int row);

    public void onCopy(int row);
}