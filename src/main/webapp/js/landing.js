// Landing Page JavaScript

// Smooth scroll for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Reveal animation on scroll
const revealElements = document.querySelectorAll('[data-reveal]');

const revealObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('revealed');
            revealObserver.unobserve(entry.target);
        }
    });
}, {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
});

revealElements.forEach(el => revealObserver.observe(el));

// Contact form handling
const contactForm = document.getElementById('contactForm');
if (contactForm) {
    contactForm.addEventListener('submit', function(e) {
        e.preventDefault();

        if (!this.checkValidity()) {
            e.stopPropagation();
            this.classList.add('was-validated');
            return;
        }

        const submitBtn = document.getElementById('submitBtn');
        const originalText = submitBtn.innerHTML;
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<i class="bi bi-hourglass-split me-2"></i>Отправка...';

        // Simulate form submission (replace with actual endpoint)
        setTimeout(() => {
            const formMessages = document.getElementById('formMessages');
            formMessages.innerHTML = `
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <strong>Спасибо!</strong> Ваше сообщение отправлено. Мы свяжемся с вами в ближайшее время.
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      `;
            contactForm.reset();
            contactForm.classList.remove('was-validated');
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalText;

            // Scroll to message
            formMessages.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        }, 1500);
    });
}

// Set topic field when buttons are clicked
const btnReportLost = document.getElementById('btnReportLost');
const btnFoundSomething = document.getElementById('btnFoundSomething');
const topicField = document.getElementById('topicField');

if (btnReportLost) {
    btnReportLost.addEventListener('click', function() {
        if (topicField) topicField.value = 'lost';
    });
}

if (btnFoundSomething) {
    btnFoundSomething.addEventListener('click', function() {
        if (topicField) topicField.value = 'found';
    });
}

// Navbar active link update on scroll
const sections = document.querySelectorAll('section[id]');
const navLinks = document.querySelectorAll('.nav-link[href^="#"]');

window.addEventListener('scroll', () => {
    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.clientHeight;
        if (window.pageYOffset >= sectionTop - 200) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.add('active');
        }
    });
});