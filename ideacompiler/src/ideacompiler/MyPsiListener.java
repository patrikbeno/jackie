package ideacompiler;

import java.util.Collection;

import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.openapi.Disposable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.PsiTreeChangeListener;
import com.intellij.psi.impl.AnyPsiChangeListener;
import com.intellij.psi.impl.source.resolve.PsiResolveHelperImpl;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MyPsiListener implements AnyPsiChangeListener, PsiTreeChangeListener, Disposable {

    MyCompiler compiler;

    public MyPsiListener(MyCompiler compiler) {
        this.compiler = compiler;
    }

    ///


    @Override
    public void beforePsiChanged(boolean b) {
        System.out.println(methodName());
    }

    @Override
    public void afterPsiChanged(boolean b) {
        System.out.println(methodName());
    }

    ///

    @Override
    public void beforeChildAddition(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void beforeChildRemoval(@NotNull PsiTreeChangeEvent event) {
        log(event);

    }

    @Override
    public void beforeChildReplacement(@NotNull PsiTreeChangeEvent event) {
        log(event);

    }

    @Override
    public void beforeChildMovement(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void beforeChildrenChange(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void beforePropertyChange(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void childAdded(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
        log(event);
        System.out.println("---");
    }

    @Override
    public void childMoved(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    @Override
    public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
        log(event);
    }

    ///
    
    String methodName() {
        return methodName(1);
    }

    String methodName(int offset) {
        return Thread.currentThread().getStackTrace()[2+offset].getMethodName();
    }
    
    PsiElement findBoundary(PsiElement element) {
        return (element == null || element instanceof PsiMethod || element instanceof PsiField || element instanceof PsiClass|| element instanceof PsiMethodCallExpression)
                ? element : findBoundary(element.getParent());
    }

    void log(PsiTreeChangeEvent event) {
        PsiElement boundary = findBoundary(event.getParent());
        System.out.printf(
                "### %s(boundary=%s, parent=%s, oldParent=%s, newParent=%s, child=%s, oldChild=%s, newChild=%s)%n",
                methodName(1), boundary,
                event.getParent(), event.getOldParent(), event.getNewParent(),
                event.getChild(), event.getOldChild(), event.getNewChild());

        if (true) return;

        PsiResolveHelperImpl resolver = new PsiResolveHelperImpl(compiler.psimanager);

        if (event.getParent() instanceof PsiJavaCodeReferenceElement) {
            PsiClass resolved = resolver.resolveReferencedClass(event.getParent().getText(), event.getParent());
            if (resolved != null) { System.out.println(resolved.getQualifiedName()); }
        }
        if (boundary instanceof PsiMethodCallExpression) {
//            PsiMethod resolved = (PsiMethod) ((PsiMethodCallExpression) boundary).getMethodExpression().resolve();
//            System.out.println(resolved.getContainingClass().getQualifiedName()+"."+resolved.getName()+"()");
            TargetElementUtil util = new TargetElementUtil();
            Collection<PsiElement> candidates = util.getTargetCandidates(((PsiMethodCallExpression) boundary).getMethodExpression());
            PsiMethod method = ((PsiMethodCallExpression) boundary).resolveMethod();
            if (method != null) {
                System.out.println("Resolved: "+method.getContainingClass().getQualifiedName()+"."+ method.getName()+"()"+method.getParameterList());
            }
            for (PsiElement candidate : candidates) {
                method = (PsiMethod) candidate;
                System.out.println("Candidate: "+method.getContainingClass().getQualifiedName()+"."+ method.getName()+"()"+method.getParameterList());
            }
//            PsiReferenceExpression callee = (PsiReferenceExpression) event.getParent();
//            JavaResolveResult[] resolved = callee.multiResolve(true);
//            for (JavaResolveResult result : resolved) {
//                System.out.println(result.getElement());
//            }
        }

    }

    ///


    @Override
    public void dispose() {
        System.out.println(methodName());
    }
}
