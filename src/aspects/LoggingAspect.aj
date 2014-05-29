package aspects;

import java.util.logging.Logger;

import org.aspectj.lang.Signature;

public aspect LoggingAspect {
private static Logger logger = Logger.getLogger("Shows");

pointcut traceMethods() : (execution(* *(..))&& !within(LoggingAspect));

    before(): traceMethods(){
        Signature sig = thisJoinPointStaticPart.getSignature();
        logger.info(
                "[Entering:] "
                    + sig.getDeclaringTypeName() + "." + sig.getName()
        );
    }
}