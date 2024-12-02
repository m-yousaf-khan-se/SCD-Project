package presenter.classDiagramPresenters;

import presenter.IPresenter;

import java.util.List;

public interface IClassDiagramPresenter extends IPresenter {
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
}
