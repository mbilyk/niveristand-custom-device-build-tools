package ni.vsbuild.v3.stages

import ni.vsbuild.v3.BuildConfiguration

class Archive extends AbstractStage {

   private String archiveLocation

   Archive(script, configuration, lvVersion) {
      super(script, 'Archive', configuration, lvVersion)
   }

   void executeStage() {
      setArchiveLocation()

      script.echo "Archiving build to $archiveLocation"
      def buildOutputDir = configuration.archive.get('build_output_dir')

      if(script.fileExists(BuildConfiguration.STAGING_DIR)) {
         buildOutputDir = BuildConfiguration.STAGING_DIR
      }

      script.bat "xcopy \"$buildOutputDir\" \"$archiveLocation\\$lvVersion\" /e /i"

      setArchiveVar()
   }

   // Builds a string of the form <archiveLocation>\\export\\<branch>\\<build_number>
   private void setArchiveLocation() {
      archiveLocation = configuration.archive.get('archive_location') +
                "\\export\\${script.env.BRANCH_NAME}\\" +
                "Build ${script.currentBuild.number}"
   }

   // Set an env var that points to the archive so dependents can find it
   private void setArchiveVar() {
      def component = script.getComponentParts()['repo']
      def depDir = "${component}_DEP_DIR"
      script.env."$depDir" = archiveLocation
   }
}
