def call() {

    node {

        echo "===== START PIPELINE ====="

        // Step 1: Read config file
        def configText = readFile 'config.properties'
        def config = configText.split('\n').collectEntries { line ->
            def parts = line.split('=')
            [(parts[0].trim()): parts[1].trim()]
        }

        def repo = config['GIT_REPO']
        def approval = config['KEEP_APPROVAL_STAGE']

        // Step 2: Clone repo
        sh "git clone ${repo}"

        // Step 3: Approval stage
        if (approval == "true") {
            input message: "Do you want to deploy?"
        }

        // Step 4: Run Ansible
        sh """
            cd ansible-assignment-5
            ansible-playbook playbook.yml -i inventory
        """

        // Step 5: Notification
        echo "Deployment Completed"

        echo "===== END PIPELINE ====="
    }
}
