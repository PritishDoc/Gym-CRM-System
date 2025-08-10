document.addEventListener('DOMContentLoaded', function() {
    // Toggle password visibility
    const togglePassword = document.querySelector('.toggle-password');
    if (togglePassword) {
        togglePassword.addEventListener('click', function() {
            const input = this.closest('.input-group').querySelector('input');
            const icon = this.querySelector('i');

            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    }

    // Handle login form submission
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const rememberMe = document.getElementById('rememberMe').checked;

            const errorElement = document.getElementById('loginError');
            errorElement.classList.add('d-none');

            try {
                const response = await fetch('/api/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ email, password })
                });

                const data = await response.json();

                if (!response.ok) {
                    throw new Error(data.message || 'Login failed');
                }

                // Store the JWT token
                localStorage.setItem('jwtToken', data.token);

                if (rememberMe) {
                    // Store in cookies for persistent login (7 days)
                    document.cookie = `jwtToken=${data.token}; path=/; max-age=${60 * 60 * 24 * 7}`;
                }

                // Close modal and redirect to dashboard
                const modal = bootstrap.Modal.getInstance(document.getElementById('loginModal'));
                modal.hide();
                window.location.href = '/dashboard';

            } catch (error) {
                errorElement.classList.remove('d-none');
                document.getElementById('errorMessage').textContent = error.message;
                console.error('Login error:', error);
            }
        });
    }

    // Check for existing JWT token on page load
    const token = localStorage.getItem('jwtToken') ||
                  document.cookie.split('; ').find(row => row.startsWith('jwtToken='))?.split('=')[1];
//
//    if (token) {
//        window.location.href = '/';
//        return;
//    }

    // Toggle password visibility
    document.querySelectorAll('.toggle-password').forEach(button => {
        button.addEventListener('click', function() {
            const input = this.previousElementSibling;
            const icon = this.querySelector('i');
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        });
    });

    // Handle switching between login and signup modals
    document.querySelectorAll('[data-bs-target="#loginModal"], [data-bs-target="#signupModal"]').forEach(link => {
        link.addEventListener('click', function() {
            const targetModal = this.getAttribute('data-bs-target');
            const currentModal = this.closest('.modal');

            if (currentModal) {
                const modal = bootstrap.Modal.getInstance(currentModal);
                modal.hide();

                setTimeout(() => {
                    const newModal = new bootstrap.Modal(document.querySelector(targetModal));
                    newModal.show();
                }, 300);
            }
        });
    });
// Add this to your existing auth.js
const signupForm = document.getElementById('signupForm');
if (signupForm) {
    signupForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const errorElement = document.getElementById('signupError');
        errorElement.classList.add('d-none');

        // Get form values
        const userData = {
            fullname: document.getElementById('fullName').value,
            email: document.getElementById('signupEmail').value,
            contactNo: document.getElementById('contactNumber').value,
            gender: document.querySelector('input[name="gender"]:checked').value,
            membership: document.getElementById('membershipType').value,
            preferredTime: document.getElementById('preferredTime').value,
            password: document.getElementById('signupPassword').value,
            confirmPassword: document.getElementById('confirmPassword').value
        };

        // Basic client-side validation
        if (userData.password !== userData.confirmPassword) {
            errorElement.classList.remove('d-none');
            document.getElementById('signupErrorMessage').textContent = "Passwords don't match";
            return;
        }

        if (userData.password.length < 8) {
            errorElement.classList.remove('d-none');
            document.getElementById('signupErrorMessage').textContent = "Password must be at least 8 characters";
            return;
        }

        try {
            const response = await fetch('/api/user/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Registration failed');
            }

            // Close modal and show success message
            const modal = bootstrap.Modal.getInstance(document.getElementById('signupModal'));
            modal.hide();

            // Show success alert
            alert('Registration successful! Please check your email for verification.');

            // Optionally redirect to login
            const loginModal = new bootstrap.Modal(document.getElementById('loginModal'));
            loginModal.show();

        } catch (error) {
            errorElement.classList.remove('d-none');
            document.getElementById('signupErrorMessage').textContent = error.message;
            console.error('Registration error:', error);
        }
    });
}
});