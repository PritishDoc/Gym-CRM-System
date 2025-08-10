//document.addEventListener('DOMContentLoaded', function () {
//  // Toggle password visibility
//  const togglePassword = document.querySelector('.toggle-password');
//  const passwordInput = document.getElementById('password');
//
//  if (togglePassword && passwordInput) {
//    togglePassword.addEventListener('click', function () {
//      const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
//      passwordInput.setAttribute('type', type);
//      const icon = this.querySelector('i');
//      icon.classList.toggle('fa-eye');
//      icon.classList.toggle('fa-eye-slash');
//    });
//  }
//
//  const loginForm = document.getElementById('loginForm');
//
//  if (loginForm) {
//    loginForm.addEventListener('submit', async function (event) {
//      event.preventDefault();
//
//      const email = document.getElementById('email').value.trim();
//      const password = document.getElementById('password').value.trim();
//
//      if (!email || !password) {
//        showError('Please fill in all fields');
//        return;
//      }
//
//      try {
//        const response = await fetch('/api/auth/login', {
//          method: 'POST',
//          headers: {
//            'Content-Type': 'application/json'
//          },
//          body: JSON.stringify({ email, password })
//        });
//
//        if (!response.ok) {
//          showError('Invalid email or password');
//          return;
//        }
//
//        const data = await response.json();
//        // Store JWT token (can use localStorage or cookies)
//        localStorage.setItem('token', data.token);
//        // Redirect to dashboard
//        window.location.href = '/dashboard';
//      } catch (err) {
//        showError('Login failed. Please try again.');
//      }
//    });
//  }
//
//  function showError(message) {
//    // Remove existing alerts
//    const oldAlert = document.querySelector('.alert-error');
//    if (oldAlert) oldAlert.remove();
//
//    const errorDiv = document.createElement('div');
//    errorDiv.className = 'alert alert-error';
//    errorDiv.innerHTML = `<i class="fas fa-exclamation-circle"></i><span>${message}</span>`;
//
//    const form = document.getElementById('loginForm');
//    form.insertBefore(errorDiv, form.firstChild);
//  }
//
//  // Google login (placeholder)
//  const googleLogin = document.querySelector('.social-login.google');
//  if (googleLogin) {
//    googleLogin.addEventListener('click', function () {
//      console.log('Google login clicked');
//    });
//  }
//});
