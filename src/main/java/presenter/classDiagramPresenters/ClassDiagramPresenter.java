package presenter.classDiagramPresenters;

import java.util.List;

public class ClassDiagramPresenter implements IClassDiagramPresenter{
    @Override
    public void addClass(String name) {
        // this funtion will be called in views
        //model function to add classes
    }

    @Override
    public void removeClass(String name) {
        //model funtion to remove classes
    }

    @Override
    public void addMethod(String className, String methodDetail) {

    }

    @Override
    public void removeMethod(String className, String methodDetail) {

    }

    @Override
    public void addField(String className, String fieldDetail) {

    }

    @Override
    public void removeField(String className, String fieldDetail) {

    }

    @Override
    public List<String> getClassNames() {
        //model funtion to get class name
        return null;
    }

    @Override
    public List<String> getMethodDetail(String className) {
        return List.of();
    }

    @Override
    public List<String> getFieldDetail(String className) {
        return List.of();
    }

    @Override
    public void addUseCase(String useCaseName) {

    }

    @Override
    public void removeUseCase(String useCaseName) {

    }
}
