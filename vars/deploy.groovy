def call() {

    echo "===== START PIPELINE ====="

    // Step 1: Read config
    def props = readProperties file: 'config.properties'

    def repo = props['GIT_REPO']
    def approval = props['KEEP_APPROVAL_STAGE']

    // Step 2: Clone Ansible repo
    sh "git clone ${repo}"

    // Step 3: Approval
    if (approval == "true") {
        input message: "Approve deployment?"
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
