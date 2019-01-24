package ni.vsbuild.steps

import ni.vsbuild.BuildConfiguration

class LvUTFStep extends LvProjectStep {

   def lvVersion

   LvUTFStep(script, mapStep, lvVersion) {
      super(script, mapStep, lvVersion)
   }

   void executeStep(BuildConfiguration configuration) {
      script.echo "SRPSM: LvUTFStep.executeStep"
   }
}
