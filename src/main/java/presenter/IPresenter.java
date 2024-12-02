package presenter;

import java.util.List;

public interface IPresenter {
 //methods related to IClassDiagramPresenter

    //from view to models
    public void addClass(String name);
    public void removeClass(String name);
    public void addMethod(String className, String methodDetail);
    public void removeMethod(String className, String methodDetail);
    public void addField(String className, String fieldDetail);
    public void removeField(String className, String fieldDetail);
    //from models to view
    public List<String> getClassNames();
    public List<String> getMethodDetail(String className);
    public List<String> getFieldDetail(String className);

 //methods related to IUseCaseDiagramPresenter
    public void addUseCase(String useCaseName);
    public void removeUseCase(String useCaseName);

}
