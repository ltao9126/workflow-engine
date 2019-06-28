import com.greatech.workflow.api.node.api.NotifyNodeExecuteState;
import com.greatech.workflow.api.node.data.NotifyNodeExecuteData;

/**
 * Created by Administrator on 2017/7/7.
 */
public class NotifyNodeExecuteStateImpl implements NotifyNodeExecuteState {
    @Override
    public void notifyState(NotifyNodeExecuteData notifyNodeExecuteData) {
        //  System.out.println(notifyNodeExecuteData.getState().getText()+"  "+notifyNodeExecuteData.getLogDate());
    }
}
