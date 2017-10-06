package ni.vsbuild.nipm.nibuild

import ni.vsbuild.AbstractPipeline
import ni.vsbuild.AbstractPipelineBuilder
import ni.vsbuild.BuildInformation

class Pipeline extends AbstractPipeline {

   static builder(script, BuildInformation buildInformation) {
      return new Builder(script, buildInformation)
   }
   
   static class Builder extends AbstractPipelineBuilder {
      
      Builder(def script, BuildInformation buildInformation) {
         super(script, buildInformation)
      }
      
      public void buildPipeline() {
         withInitialCleanStage()
         withCheckoutStage()
         
         withSetupStage()
         
         withBuildStage()
         withUnitTestStage()
         withPackageStage()
         
         withCleanupStage()
         
         return new Pipeline(this)
      }
   }
   
   private Pipeline(builder) {
      super(builder)
   }
   
   void execute() {      
      // this is not used for nibuild execution, but is required for creating the executor
      def lvVersion = buildInformation.lvVersions.sort()[0]
      
      script.node(getNodeLabel(lvVersion)) {
         def executor = buildInformation.createExecutor(script, lvVersion)
         executeStages(buildStages, executor)
      }
   }
}
