package ni.vsbuild.v3.stages

class Build extends AbstractStepStage {

   Build(script, configuration, lvVersion) {
      super(script, 'Build', configuration, lvVersion)
   }

   void executeStage() {
      executeSteps('build')
   }
}
