# **************************************************************************** #
#                                                                              #
#                                                         :::      ::::::::    #
#    setup.py                                           :+:      :+:    :+:    #
#                                                     +:+ +:+         +:+      #
#    By: ShnurD6 <shnurd6@mail.ru>                  +#+  +:+       +#+         #
#                                                 +#+#+#+#+#+   +#+            #
#    Created: 2019/09/07 17:08:13 by ShnurD6           #+#    #+#              #
#    Updated: 2019/09/07 17:59:31 by ShnurD6          ###   ########.fr        #
#                                                                              #
# **************************************************************************** #

from distutils.core import setup, Extension
setup(name = 'TcoModule', version = '1.0',  \
   ext_modules = [Extension('TcoModule', ['tree_custom_order.cpp'], extra_compile_args = ["-O3"])])