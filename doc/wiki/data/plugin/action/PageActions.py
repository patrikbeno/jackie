# -*- coding: iso-8859-1 -*-

import re
from MoinMoin import config, wikiutil
from MoinMoin.Page import Page

# Ripped from the LikePages action
def execute(pagename, request):
    _ = request.getText
    from MoinMoin.formatter.text_html import Formatter
    request.formatter = Formatter(request)

    request.emit_http_headers()

    # This action generate data using the user language
    request.setContentLanguage(request.lang)
    request.theme.send_title(_('Actions for %s') % pagename, pagename=pagename)
        
    # Start content - IMPORTANT - without content div, there is no
    # direction support!
    request.write(request.formatter.startContent("content"))

    # Just list the actions
    request.write(availableactions(request))

    # End content and send footer
    request.write(request.formatter.endContent())
    request.theme.send_footer(pagename)

# Make a link to action
def actionlink(request, action, title, comment=''):
    page = request.page
    _ = request.getText
    # Always add spaces: AttachFile -> Attach File 
    # XXX TODO do not make a page object just for split_title
    title = Page(request, title).split_title(request) #, force=1)
    # Use translated version if available
    title = _(title, formatted=False)
    params = '%s?action=%s' % (page.page_name, action)
    link = wikiutil.link_tag(request, params, _(title))
    return u''.join([ u'<li>', link, comment, u'</li>' ])


# Rippped from the theme code
def availableactions(request):
    page = request.page
    _ = request.getText
    html = ''
    links = []
    available = request.getAvailableActions(page)
#    try:
#        available = available.keys()
#    except AttributeError:
#        pass
    for action in available:
        links.append(actionlink(request, action, action))
    if page.isWritable() and request.user.may.write(page.page_name):
        links.append(actionlink(request, 'edit', 'EditText'))
    if request.user.valid and request.user.email:
        action = ("Subscribe", "Unsubscribe")[request.user.isSubscribedTo([page.page_name])]
        links.append(actionlink(request, 'subscribe', action))
    if request.user.valid:
        links.append(actionlink(request, 'userform&logout=logout', 'Logout'))
    links.append(actionlink(request, 'print', 'PrintView'))
    links.append(actionlink(request, 'raw', 'ViewRawText'))
    links.append(actionlink(request, 'refresh', 'DeleteCache'))
    links.append(actionlink(request, 'userprefs', 'UserPreferences'))
    html = u'<ul>%s</ul>' % u''.join(links)
    return html
